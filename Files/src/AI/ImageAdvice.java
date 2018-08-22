package AI;
/*	Singleton
 * 
 */
import java.util.Random; // Random number

public class ImageAdvice {
	// Scores the parsed string tokens.
	private String[] tokens;
	// Random number
	Random rand;
	public ImageAdvice() {
		rand = new Random();
	}
	// Take in ImageLearningModel string
	public String processModelString(String modelString) {
		// Gonna parse this string by the delimiter '|'
		String delimiter = "[|]";
		tokens = modelString.split(delimiter);
		// We return this
		String finalMessage = "";
		
		// This now contains score in index 0, then the 11 attributes following
		/*
		 * In this order:
		 * Score
		 * 
		 * Light
		 * BalancingElements
		 * DoF
		 * ColorHarmony
		 * Content
		 * MotionBlur
		 * RuleOfThirds
		 * Object
		 * Repetition
		 * VividColor
		 * Symmetry
		 * 
		 */
		boolean isFirst = false;
		for (String item : tokens) {
			if (isFirst == false) {
				isFirst = true;
			} else {
				finalMessage += "|";
			}
			System.out.println("Advice loop: " + item);
			if (item.equals("Score")) {
				finalMessage += scoreAdvice();
			}
			else if (item.equals("Light")) {
				finalMessage += lightAdvice();

			}
			else if (item.equals("BalancingElements")) {
				finalMessage += balancingElementsAdvice();

			}
			else if (item.equals("DoF")) {
				finalMessage += dofAdvice();

			}
			else if (item.equals("ColorHarmony")) {
				finalMessage += colorHarmonyAdvice();

			}
			else if (item.equals("Content")) {
				finalMessage += contentAdvice();

			}
			else if (item.equals("MotionBlur")) {
				finalMessage += motionBlurAdvice();

			}
			else if (item.equals("RuleOfThirds")) {
				finalMessage += ruleOfThirdsAdvice();

			}
			else if (item.equals("Object")) {
				finalMessage += objectAdvice();

			}
			else if (item.equals("Repetition")) {
				finalMessage += repetitionAdvice();

			}
			else if (item.equals("VividColor")) {
				finalMessage += vividColorAdvice();

			}
			else if (item.equals("Symmetry")) {
				finalMessage += symmetryAdvice();

			}
			
		}
		if (tokens.length == 0) {
			finalMessage += scoreCongratulations();
		}
		System.out.println("LOLL");
		return finalMessage;
	}
	/*
	 * Output score congratulations
	 * Remember: this only triggers if your score is high!
	 */
	public String scoreCongratulations() {
		String[] advice = {
			"This is an exceptional photo!",
			"Congratulations on your photo!",
			"We love your photo!",
			"Our robot monkeys have reached a consensus: this is a great photo!"


		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	
	/*
	 * Output score advice.
	 * Remember: this only triggers if your score is low!
	 */
	public String scoreAdvice() {
		String[] advice = {
			"Try looking at our individual attribute advice!",
			"There are many ways to improve your photo taking. Our individual score advice may be able to help!",
			"Don't ever be discouraged!",
			"We know you can do better!"
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	
	/*
	 * Output score advice.
	 * Remember: this only triggers if your score is low!
	 */
	public String lightAdvice() {
		String[] advice = {
			"This looks great, but your lighting is a bit off. You can definitely do better!",
			"Getting good lighting is like carrying 100 kgs. of bricks: it’s hard. But you’ll get there!",
			"You took a good picture, but we need to work on your lighting next time."
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	/*
	 * Output balancing elements advice.
	 * 
	 */
	public String balancingElementsAdvice() {
		String[] advice = {
			"A balanced composition is hard to achieve, and you missed the mark this time. But you’ll get there!",
			"A picture is like a budget: it needs to be balanced. Work on balancing the space in your photo next time!",
			"Great picture! It'll be even better if you work on your object balance."
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	
	public String dofAdvice() {
		String[] advice = {
			"Working on your depth of field can make your pictures far more dramatic.",
			"If you want to boost your Depth of Field score, try using portrait mode on an iPhone!",
			"Creating interesting depth of field is tough, but worth it. Try focusing your lens on individual objects!"
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	public String colorHarmonyAdvice() {
		String[] advice = {
			"Your colors were a bit disjunct. Maybe try coordinating with the background next time!",
			"A picture’s colors should sing in harmony, but these colors fell flat. There’s always next time.",
			"Your color coordination was off. Try to be more harmonious in your next selfie!"
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	public String contentAdvice() {
		String[] advice = {
			"Our monkeys didn't find interesting content in your photo. Try changing the subject of your photo!",
			"We know your photo is amazing, but our mechanical monkeys didn't like the content. Maybe try another subject?",
			"Our machine learning monkeys thought your picture was uninteresting. Maybe hold a pink cactus next time?"
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	public String motionBlurAdvice() {
		String[] advice = {
			"You didn’t have much motion blur in your picture. It could make it more dynamic!",
			"A little motion blur can really make you feel like your picture is moving. Go for it next time!",
			"This looks amazing, but maybe a bit of motion blur in the background would make the picture pop?"
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	public String ruleOfThirdsAdvice() {
		String[] advice = {
			"The rule of thirds is super important to framing a great picture. Try moving the center a little right or left next time.",
			"All the fancy photographers use the rule of thirds, and you should too. Give it a Google!",
			"You didn’t comply to the rule of thirds. Try to… it will make your picture more professional looking!"
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	public String objectAdvice() {
		String[] advice = {
			"Your picture doesn’t emphasize the frontmost elements. Our monkeys were distracted!",
			"Our machine learning monkeys say things might be out of focus in your picture. Either that, or they got to the beer again.",
			"Make sure things are emphasized in the foreground next time. Show us what you've got!"
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	public String repetitionAdvice() {
		String[] advice = {
			"Your picture isn’t too repetitive. Maybe it will look better with some patterns in the background?",
			"Your picture is very heterogeneous. Go for some homogeny next time.",
			"Our monkeys like patterns."
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	public String vividColorAdvice() {
		String[] advice = {
			"Your colors don’t really pop in this pic. Try to be a bit more vivid.",
			"Contrasting, bright colors make things stand out!",
			"The more vivid the colors are in your image, the harder it is to look away. Make it happen next time!"
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	public String symmetryAdvice() {
		String[] advice = {
			"Symmetry can make for a beautiful image. Try to get some in the frame next time!",
			"Our monkeys enjoy symmetry. Among other things.",
			"Symmetry sometimes makes for quality in a photo! Try focusing on it next time."
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	/*
	 * This is a placeholder: it represents the format for advice.
	 */
	public String genericAdvice() {
		String[] advice = {
			"",
			"",
			""
		};
		// randomize number: this is the index
		int randomGenerator = rand.nextInt(advice.length);
		return advice[randomGenerator];
	}
	
	public static void main(String[] args) {
		ImageAdvice test = new ImageAdvice();
		System.out.println("High Score Congratulations: " + test.scoreCongratulations());
		System.out.println("Low Score Advice: " + test.scoreAdvice());
		System.out.println("Low Light Advice: " + test.lightAdvice());
		System.out.println("Low BalancingElements Advice: " + test.balancingElementsAdvice());
		System.out.println("Low DoF Advice: " + test.dofAdvice());
		System.out.println("Low ColorHarmony Advice: " + test.colorHarmonyAdvice());
		System.out.println("Low Content Advice: " + test.contentAdvice());
		System.out.println("Low MotionBlur Advice: " + test.motionBlurAdvice());
		System.out.println("Low RuleOfThirds Advice: " + test.ruleOfThirdsAdvice());
		System.out.println("Low Object Advice: " + test.objectAdvice());
		System.out.println("Low Repetition Advice: " + test.repetitionAdvice());
		System.out.println("Low VividColor Advice: " + test.vividColorAdvice());
		System.out.println("Low Symmetry Advice: " + test.symmetryAdvice());

	}

}


