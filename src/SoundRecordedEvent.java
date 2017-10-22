import java.awt.event.ActionEvent;

/**
 * Wrapper class for the SoundRecordedEvent. Although not technically needed the skeleton is left should any
 * further functionality be needed in the future.
 */
class SoundRecordedEvent extends ActionEvent {
    SoundRecordedEvent(Object source, int id, String command) {
        super(source, id, command);
    }
}
