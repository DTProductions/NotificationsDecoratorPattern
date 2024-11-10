public class AudioNotificationDecorator extends NotificationDecorator{
    public AudioNotificationDecorator(Notification notification){
        super(notification);
    }

    @Override
    public void send(String message){
        super.send(message);
        playAudioMessage(message);
    }

    private void playAudioMessage(String message) {
        String espeakPath = "C:\\Program Files (x86)\\eSpeak\\command_line\\espeak.exe";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(espeakPath, message);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
