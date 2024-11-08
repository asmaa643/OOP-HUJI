import java.util.Scanner;


/**
 * A Class that runs a chat between ChatterBots.
 */
public class Chat {
    public static void main(String[] args) {
        ChatterBot[] chatterBots = new ChatterBot[2];
        //Legal and illegal requests for ChatterBot number 1
        String[] legalChat1 = {String.format("say %s ", ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE),
                String.format("Fine, %s", ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE), "Why?"};
        String[] illegalChat1 = {"say I like sport!", "say sport", "Hi", "say Hi"};
        chatterBots[0] = new ChatterBot("Sammy", legalChat1, illegalChat1);

        //Legal and illegal requests for ChatterBot number 2
        String[] legalChat2 = {String.format("you want me to say %s ? okay: %s",
                ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE, ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE),
                "I don't want to say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE, "You say it!"};
        String[] illegalChat2 = {String.format("what %s ", ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST),
                String.format("say %s ", ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST), "Ha?"};
        chatterBots[1] = new ChatterBot("Ruthy", legalChat2, illegalChat2);
        String firstStatement = "Hello!";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            for (ChatterBot bot : chatterBots) {
                String statement = bot.replyTo(firstStatement);
                System.out.print(bot.getName() + ": " + statement);
                scanner.nextLine();
                firstStatement = statement;
            }
        }
    }
}
