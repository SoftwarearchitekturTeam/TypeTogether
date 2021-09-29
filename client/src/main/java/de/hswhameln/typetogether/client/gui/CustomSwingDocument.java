package de.hswhameln.typetogether.client.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.*;
import javax.swing.undo.UndoableEdit;

public class CustomSwingDocument extends PlainDocument {


    /**
     * Inserts some content into the document.
     * Inserting content causes a write lock to be held while the
     * actual changes are taking place, followed by notification
     * to the observers on the thread that grabbed the write lock.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html">Concurrency
     * in Swing</A> for more information.
     *
     * @param offs the starting offset &gt;= 0
     * @param str the string to insert; does nothing with null/empty strings
     * @param a the attributes for the inserted content
     * @exception BadLocationException  the given insert position is not a valid
     *   position within the document
     * @see Document#insertString
     */
    @Override //
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        this.insertString(offs, str, a, new DefaultDocumentEvent(offs, str.length(), DocumentEvent.EventType.INSERT));
    }

    public void insertStringProgrammatically(int offs, String str, AttributeSet a) throws BadLocationException {
        this.insertString(offs, str, a, new MyDefaultDocumentEvent(offs, str.length(), DocumentEvent.EventType.INSERT));
    }

    private void insertString(int offs, String str, AttributeSet a, DefaultDocumentEvent e) throws BadLocationException {
        if ((str == null) || (str.length() == 0)) {
            return;
        }
        if (offs > getLength()) {
            throw new BadLocationException("Invalid insert", getLength());
        }
        DocumentFilter filter = getDocumentFilter();

        writeLock();

        try {
            if (filter != null) {
                throw new RuntimeException("This will hopefully never be accessed...");
            } else {
                handleInsertString(offs, str, a, e);
            }
        } finally {
            writeUnlock();
        }
    }

    /**
     * Performs the actual work of inserting the text; it is assumed the
     * caller has obtained a write lock before invoking this.
     */
    private void handleInsertString(int offs, String str, AttributeSet a, DefaultDocumentEvent e)
            throws BadLocationException {
        if ((str == null) || (str.length() == 0)) {
            return;
        }
        UndoableEdit u = getContent().insertString(offs, str);

        if (u != null) {
            e.addEdit(u);
        }

        insertUpdate(e, a);
        // Mark the edit as done.
        e.end();
        fireInsertUpdate(e);
        // only fire undo if Content implementation supports it
        // undo for the composed text is not supported for now
        if (u != null && (a == null || !a.isDefined(StyleConstants.ComposedTextAttribute))) {
            fireUndoableEditUpdate(new UndoableEditEvent(this, e));
        }
    }

    public class MyDefaultDocumentEvent extends DefaultDocumentEvent {


        /**
         * Constructs a change record.
         *
         * @param offs the offset into the document of the change &gt;= 0
         * @param len  the length of the change &gt;= 0
         * @param type the type of event (DocumentEvent.EventType)
         * @since 1.4
         */
        public MyDefaultDocumentEvent(int offs, int len, EventType type) {
            super(offs, len, type);
        }
    }
}
