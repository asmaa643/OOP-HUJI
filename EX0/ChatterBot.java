import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * @author Dan Nirel
 */
class ChatterBot {
	/**
	 * The prefix for a legal request.
	 */
	static final String REQUEST_PREFIX = "say ";
	/**
	 * The placeholder in legal request.
	 */
	static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
	/**
	 * The placeholder in illegal request.
	 */
	static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";


	String name;

	Random rand = new Random();
	String[] repliesToIllegalRequest;
	String[] legalRequestsReplies;

	/**
	 * A constructor of a single chatterbot
	 * @param name: Chatterbot's name
	 * @param legalRequestsReplies The replies of legal requests.
	 * @param repliesToIllegalRequest The replies of illegal requests.
	 */
	ChatterBot(String name, String[] legalRequestsReplies, String[] repliesToIllegalRequest){
		this.name = name;
		this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
		this.legalRequestsReplies = new String[legalRequestsReplies.length];
		for(int i = 0 ; i < legalRequestsReplies.length ; i = i+1) {
			this.legalRequestsReplies[i] = legalRequestsReplies[i];
		}
		for(int i = 0 ; i < repliesToIllegalRequest.length ; i = i+1) {
			this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
		}
	}

	/**
	 * Chatterbot's name getter.
	 * @return the chatterbot's name.
	 */
	String getName(){
		return this.name;
	}

	/**
	 * Replies to the legal and illegal statements
	 * @param statement to reply to
	 * @return reply to statement
	 */
	String replyTo(String statement) {
		if(statement.startsWith(REQUEST_PREFIX)) {
			return replyToLegalRequest(statement);

		}
		return replyToIllegalRequest(statement);
	}

	/**
	 * Replaces the holder with a random pattern.
	 * @param patterns to choose from it randomly
	 * @param placeholder to replace.
	 * @param replacement the word to be replaced
	 * @return response pattern after replacing
	 */
	String replacePlaceholderInARandomPattern(String[] patterns, String placeholder, String replacement){
		int randomIndex = rand.nextInt(patterns.length);
		String responsePattern = patterns[randomIndex];
		return responsePattern.replaceAll(placeholder, replacement);
	}

	/**
	 * Replies to legal request.
	 * @param statement to reply to
	 * @return reply
	 */
	String replyToLegalRequest(String statement) {
		//we don’t repeat the request prefix, so delete it from the reply
		String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
		return replacePlaceholderInARandomPattern(legalRequestsReplies,
				PLACEHOLDER_FOR_REQUESTED_PHRASE, phrase);
	}

	/**
	 * Replies to illegal request.
	 * @param statement to reply to
	 * @return reply
	 */
	String replyToIllegalRequest(String statement) {
		return replacePlaceholderInARandomPattern(repliesToIllegalRequest,
				PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement);
	}
}
