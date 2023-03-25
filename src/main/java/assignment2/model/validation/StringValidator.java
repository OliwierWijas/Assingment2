package assignment2.model.validation;

public class StringValidator
{
  public final static int NICKNAME_MINIMAL_LENGTH = 6;
  public final static int NICKNAME_MAXIMUM_LENGTH = 14;
  public final static int PASSWORD_MINIMAL_LENGTH = 8;
  private static void IllegalArgument(String message)
  {
    throw new IllegalArgumentException(message);
  }

  public static void validateNickname(String nickname)
  {
    if (nickname == null || nickname.length() < NICKNAME_MINIMAL_LENGTH)
      IllegalArgument("Nickname cannot be shorter than " + NICKNAME_MINIMAL_LENGTH + " characters");
    else if (nickname.length() > NICKNAME_MAXIMUM_LENGTH)
      IllegalArgument("Nickname cannot be longer than " + NICKNAME_MAXIMUM_LENGTH + " characters.");
  }

  public static void validatePassword(String password)
  {
    if (password == null || password.length() < PASSWORD_MINIMAL_LENGTH)
      IllegalArgument("Password cannot be shorter than " + PASSWORD_MINIMAL_LENGTH + " characters.");
    boolean lowercase = false;
    boolean uppercase = false;
    boolean digit = false;
    for(int i = 0; i < password.length(); i++) {
      char ch = password.charAt(i);
      lowercase = lowercase || Character.isLowerCase(ch);
      uppercase = uppercase || Character.isUpperCase(ch);
      digit = digit || Character.isDigit(ch);
    }
    if (!lowercase)
      IllegalArgument("Password needs lowercase characters.");
    else if (!uppercase)
      IllegalArgument("Password needs uppercase characters.");
    else if (!digit)
      IllegalArgument("Password needs digits.");
  }
}
