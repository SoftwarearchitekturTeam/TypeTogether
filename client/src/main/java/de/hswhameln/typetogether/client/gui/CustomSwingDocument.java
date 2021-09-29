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

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        this.remove(offs, len, new DefaultDocumentEvent(offs, len, DocumentEvent.EventType.REMOVE));
    }

    public void removeProgrammatically(int offs, int len) throws BadLocationException {
        this.remove(offs, len, new MyDefaultDocumentEvent(offs, len, DocumentEvent.EventType.REMOVE));
    }
    /**
     * Removes some content from the document.
     * Removing content causes a write lock to be held while the
     * actual changes are taking place.  Observers are notified
     * of the change on the thread that called this method.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html">Concurrency
     * in Swing</A> for more information.
     *
     * @param offs the starting offset &gt;= 0
     * @param len the number of characters to remove &gt;= 0
     * @exception BadLocationException  the given remove position is not a valid
     *   position within the document
     * @see Document#remove
     */
    public void remove(int offs, int len, DefaultDocumentEvent chng) throws BadLocationException {
        DocumentFilter filter = getDocumentFilter();

        writeLock();
        try {
            if (filter != null) {
                throw new RuntimeException("This will hopefully never be accessed...");
            }
            else {
                handleRemove(offs, len, chng);
            }
        } finally {
            writeUnlock();
        }
    }

    /**
     * Performs the actual work of the remove. It is assumed the caller
     * will have obtained a <code>writeLock</code> before invoking this.
     */
    void handleRemove(int offs, int len, DefaultDocumentEvent chng) throws BadLocationException {
        if (len > 0) {
            if (offs < 0 || (offs + len) > getLength()) {
                throw new BadLocationException("Invalid remove",
                        getLength() + 1);
            }

            removeUpdate(chng);
            UndoableEdit u = getContent().remove(offs, len);
            if (u != null) {
                chng.addEdit(u);
            }
            postRemoveUpdate(chng);
            // Mark the edit as done.
            chng.end();
            fireRemoveUpdate(chng);
            // only fire undo if Content implementation supports it
            // undo for the composed text is not supported for now
            if ((u != null)) {
                fireUndoableEditUpdate(new UndoableEditEvent(this, chng));
            }
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
