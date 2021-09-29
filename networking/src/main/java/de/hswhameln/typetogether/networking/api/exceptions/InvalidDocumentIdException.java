package de.hswhameln.typetogether.networking.api.exceptions;

public class InvalidDocumentIdException extends FunctionalException {


    public InvalidDocumentIdException(String message) {
        super(message);
    }

    public static class DocumentDoesNotExistException extends InvalidDocumentIdException {
        public DocumentDoesNotExistException(String documentId) {
            super("Document with id " + documentId + " does not exist.");
        }
    }

    public static class DocumentAlreadyExistsException extends InvalidDocumentIdException {
        public DocumentAlreadyExistsException(String documentId) {
            super("Document with id " + documentId + " already exist.");
        }
    }

}
