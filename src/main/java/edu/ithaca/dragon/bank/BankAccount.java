package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post take the amount into a account 
     * throws if neg or zero to deposit  // savingAccount.deposit(20) 
     */
    public void deposit (double amount) throws IllegalArgumentException{
    
         if (isAmountValid(amount) == false) { 
            throw new IllegalArgumentException("Invalid amount to deposit");
        }
        else{
            balance += amount;
        }
        
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * 
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (isAmountValid(amount) == false) { 
            throw new IllegalArgumentException("Invalid withdrawl amount");
        }
        else if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * @post withdraw the amount from one account and deposit it into the other account 
     * throws IllegalArgumentException:
     * when withdraw less then the amount in the bankaccount 
     * 
     */
    public void transfer (double amount, BankAccount accountFrom, BankAccount accountTo) throws IllegalArgumentException{
        if (isAmountValid(amount) == false) { 
            throw new IllegalArgumentException("Invalid to amount transfer");
        }
        try{
            accountFrom.withdraw(amount);
            accountTo.deposit(amount);
        } catch(Exception e) {
            throw new IllegalArgumentException();
        }
    
    }
    
    /**
     * @post takes a double and returns true if the amount 
     * is positive and has two decimal points or less, and false otherwise. 
     */
    public static boolean isAmountValid (double amount) {
        if (amount >.0 ){
            return true;
        }
        else{
            return false;
        }
        
    }


    public static boolean isEmailValid(String email){
        email.toLowerCase();
        if (email.indexOf('@') == -1 || email.indexOf('.') == -1 || email == ""){
            return false;
        }
        else {
            //Divide the address into recipient, domainName, suffix
            int atIndex = email.indexOf('@');
            String recipient = "";
            for (int i = 0; i < atIndex; i++) {
                recipient = recipient + email.charAt(i);
            }

            int dotIndex = email.indexOf('.', atIndex + 1);
            String domainName = "";
            for (int j = atIndex + 1; j < dotIndex; j++) {
                domainName = domainName + email.charAt(j);
            }

            String suffix = "";
            for (int l = dotIndex + 1; l < email.length(); l++) {
                suffix = suffix + email.charAt(l);
            }

            //Check that recipient is not empty
            if (recipient.length() == 0) return false;
            //First and last characters of recipient must be a letter
            //Tests and implementation do not account for number at beginning or end
            if (recipient.charAt(0) > 122 || recipient.charAt(0) < 97) {
                return false;
            }
            if (recipient.charAt(recipient.length() - 1)> 122 || recipient.charAt(recipient.length() - 1) < 97) {
                return false;
            }

            //Domain name only has letters or dashes in it
            for (int n = 0; n < domainName.length(); n++) {
                if ((domainName.charAt(n) < 97 || domainName.charAt(n) > 122) && domainName.charAt(n) != '-' ) {
                    return false;
                }
            }
            //Check that domainName is not empty
            if (domainName.length() == 0) return false;

            //Check that suffix is not empty
            if (suffix.length() == 0) return false;

            return true;
        }
    }
}
