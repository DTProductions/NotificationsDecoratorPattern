import java.util.Scanner;

public class Main {

    private static final String SENDER_EMAIL = "";
    private static final String SENDER_NAME = "Diogo Tosta Silva";
    private static final String RECIPIENT_EMAIL = "";
    private static final String SENDER_APP_PASSWORD = "";

    public static void main(String[] args){
        System.out.println("Notifications (Decorator Design Pattern Example)");
        System.out.println("------------------------------");
        System.out.println("Message: ");

        Scanner terminal = new Scanner(System.in);
        String message = terminal.nextLine();

        Notification notification = new BasicNotification();
        int layer = 1;
        while (true){
            System.out.println("---------------------------------------");
            System.out.println("Add decorator at layer number " + layer);
            System.out.println("1: Audio Notification");
            System.out.println("2: Email Notification");
            System.out.println("3: End");

            String option = terminal.nextLine();
            if(option.equals("3")){
                break;
            }

            switch (option){
                case "1":
                    notification = new AudioNotificationDecorator(notification);
                    break;
                case "2":
                    notification = new EmailNotificationDecorator(notification, SENDER_EMAIL, SENDER_NAME, SENDER_APP_PASSWORD, RECIPIENT_EMAIL);
                    break;
            }

            layer++;
        }

        System.out.println("Sending notification...");
        notification.send(message);
    }
}
