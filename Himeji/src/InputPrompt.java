//Program Name:   InputPrompt.java
//Date:           3/13/2020
//Programmer:     Shawn Carter
//Description:    This class houses methods for prompting user input.

import java.util.Scanner;

public class InputPrompt 
{
	
	/**
	 * Prompts the user to input an integer. Will continue to re-prompt
	 * until the user enters a valid value.
	 * @param prompt Message telling the user what to enter
	 * @param input  Scanner object used to accept the input
	 * @return       Integer value provided by the user
	 */
	public static int intPrompt(String prompt, Scanner input)
	{
		int result = 0;
		boolean isValid = false;
		
		do
		{
			try
			{
				System.out.print(prompt);
				result = input.nextInt();
				isValid = true;
			}
			catch (Exception e) 
			{
				System.out.println("Input was not valid. Please only enter an integer.");
			}
			finally 
			{
				input.nextLine();
			}
		} while (!isValid);
		
		return result;
	}

	/**
	 * Prompts the user to input an float. Will continue to re-prompt
	 * until the user enters a valid value.
	 * @param prompt Message telling the user what to enter
	 * @param input  Scanner object used to accept the input
	 * @return       Float value provided by the user
	 */
	public static float floatPrompt(String prompt, Scanner input)
	{
		float result = 0;
		boolean isValid = false;
		
		do
		{
			try
			{
				System.out.print(prompt);
				result = input.nextFloat();
				isValid = true;
			}
			catch (Exception e) 
			{
				System.out.println("Input was not valid. Please only enter a float.");
			}
			finally 
			{
				input.nextLine();
			}
		} while (!isValid);
		
		return result;
	}

	/**
	 * Prompts the user to input an line of text.
	 * @param prompt Message telling the user what to enter
	 * @param input  Scanner object used to accept the input
	 * @return       String object of the line provided by the user
	 */
	public static String stringPrompt(String prompt, Scanner input)
	{
		String result = "";
		
		System.out.print(prompt);
		result = input.nextLine();
		
		return result;
	}

}
