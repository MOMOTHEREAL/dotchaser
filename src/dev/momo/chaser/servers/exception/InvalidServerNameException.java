package dev.momo.chaser.servers.exception;

/**
 * Thrown when the given server name is rejected by the host
 */
public class InvalidServerNameException extends Exception{

    public InvalidServerNameException() {
        super();
    }

    public InvalidServerNameException(String message) {
        super(message);
    }


}
