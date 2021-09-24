package de.hswhameln.typetogether.networking.shared;

import java.net.Socket;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;
import de.hswhameln.typetogether.networking.util.IOUtils;

public class DocumentClientProxy extends AbstractClientProxy implements Document {

    public DocumentClientProxy(Socket socket) {
        super(socket);
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        this.safelyExecute(() -> {
            this.chooseOption("1");
            IOUtils.expectResponseCodeSuccess(this.in);
        });
    }

    @Override
    public void removeChar(User author, DocumentCharacter character) {
        this.safelyExecute(() -> {
            this.chooseOption("2");
            IOUtils.expectResponseCodeSuccess(this.in);
        });
    }

    @Override
    public String getFuncId() {
        return this.safelyExecute(() -> {
            this.chooseOption("3");
            IOUtils.expectResponseCodeSuccess(this.in);
            String funcId = this.in.readLine();
            logger.fine("getFuncId returned " + funcId);
            return funcId;
        });
    }
}
