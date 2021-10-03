package de.hswhameln.typetogether.networking.api.exceptions;

public class InvalidDocumentIdException extends FunctionalException {


    public InvalidDocumentIdException(String message) {
        super(message);
    }

    public static class DocumentDoesNotExistException extends InvalidDocumentIdException {

        public DocumentDoesNotExistException(String message) {
            super(message);
        }

        public static DocumentDoesNotExistException create(String documentId) {
            return new DocumentDoesNotExistException("Document with id " + documentId + " does not exist.");
        }
    }

    public static class DocumentAlreadyExistsException extends InvalidDocumentIdException {
        public DocumentAlreadyExistsException(String message) {
            super(message);
        }
        public static DocumentAlreadyExistsException create(String documentId) {
            return new DocumentAlreadyExistsException("Document with id " + documentId + " already exist.");
        }
    }

}
