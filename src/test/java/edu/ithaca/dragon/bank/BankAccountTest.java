package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        //TODO amount element if like neg or double
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        bankAccount.withdraw(100);

    }

    @Test
    void withdrawTest () throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance()); //larger amount

        bankAccount.withdraw(30);
        assertEquals(70, bankAccount.getBalance());  //small amount

        bankAccount.withdraw(.40);
        assertEquals(69.60, bankAccount.getBalance());  //cent
           
        //over draws(not enough money)
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(400000));

        //IllegalArgumentException 
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-300));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-300.00));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0));
    }

    @Test
    void isEmailValidTest(){
        
        assertFalse( BankAccount.isEmailValid("")); //no email
        
        //period placement
        assertTrue( BankAccount.isEmailValid("abc.def@mail-archive.com")); //placement of two period
        assertFalse( BankAccount.isEmailValid(".abc@mail.com")); //placement of two period
        assertFalse( BankAccount.isEmailValid("abc@.com")); //at near the period
        assertFalse( BankAccount.isEmailValid("abc@com.")); // period at the end
        
        //at placement
        assertFalse( BankAccount.isEmailValid("abc-@mail.com")); //no character before at
        assertFalse( BankAccount.isEmailValid("@mail.com")); // at first
        assertFalse( BankAccount.isEmailValid("abcmail.com@")); // at last

        //Things bewteen at and period
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //one letter bewteen at and period
        assertTrue( BankAccount.isEmailValid("abc@mail.com")); //more than one letter bewteen at and period
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com")); //no other chararcters bewteen at and period 

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100)); //no cent
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", -100)); //negative
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", -100.00)); //negative with cent
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", -100.75)); ////negative with value cent
        
    }

    @Test
    void depositTest () throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200); 

        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance()); //large amount

        bankAccount.deposit(20);
        assertEquals(320, bankAccount.getBalance()); //small amount

        bankAccount.deposit(20.00);
        assertEquals(340.00, bankAccount.getBalance()); //no cent

        bankAccount.deposit(0.40);
        assertEquals(340.40, bankAccount.getBalance()); //cent
           
        //IllegalArgumentException 
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-300)); //no cent
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-300.00)); //cent
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-300.75)); //cent and negative
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0)); //no amount
    }

    @Test
    void transferTest() throws InsufficientFundsException {
        BankAccount bankAccountFrom = new BankAccount("a@b.com", 200);
        BankAccount bankAccountTo = new BankAccount("a@b.com", 400);

        bankAccountFrom.transfer(20.50, bankAccountFrom, bankAccountTo); //cent
        assertEquals(420.50, bankAccountTo.getBalance()); 
        assertEquals(179.50, bankAccountFrom.getBalance()); 

        bankAccountFrom.transfer(100.00, bankAccountFrom, bankAccountTo); //large
        assertEquals(520.50, bankAccountTo.getBalance()); 
        assertEquals(79.50, bankAccountFrom.getBalance());

        bankAccountFrom.transfer(9, bankAccountFrom, bankAccountTo); //small
        assertEquals(529.50, bankAccountTo.getBalance()); 
        assertEquals(70.50, bankAccountFrom.getBalance());


        //negative
        assertThrows(IllegalArgumentException.class, ()-> bankAccountFrom.transfer(-300, bankAccountFrom, bankAccountTo));
        assertThrows(IllegalArgumentException.class, ()-> bankAccountFrom.transfer(-300.00, bankAccountFrom, bankAccountTo));
        assertThrows(IllegalArgumentException.class, ()-> bankAccountFrom.transfer(-300.75, bankAccountFrom, bankAccountTo));
        assertThrows(IllegalArgumentException.class, ()-> bankAccountFrom.transfer(0, bankAccountFrom, bankAccountTo));
    
    }

    @Test
    void isAmountValidTest () {
       
        //Vaild cases, high and low amount, cents
        assertTrue(BankAccount.isAmountValid(40.0));
        assertTrue(BankAccount.isAmountValid(0.40)); 
        assertTrue(BankAccount.isAmountValid(100.00));
        assertTrue(BankAccount.isAmountValid(100.50));

        assertFalse(BankAccount.isAmountValid(0)); //zero case
        assertTrue(BankAccount.isAmountValid(4)); //no double

        //negative cases
        assertFalse(BankAccount.isAmountValid(-20)); 
        assertFalse(BankAccount.isAmountValid(-.20)); 
        assertFalse(BankAccount.isAmountValid(-20.50)); 
        assertFalse(BankAccount.isAmountValid(-35.00)); 
        assertFalse(BankAccount.isAmountValid(-430.0));   
    }

}