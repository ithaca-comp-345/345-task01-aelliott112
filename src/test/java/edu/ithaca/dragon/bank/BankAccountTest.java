package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }


    @Test
    void withdrawTest () throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200); //200-100=100-30=70
        bankAccount.withdraw(100);
        bankAccount.withdraw(30);

        assertEquals(100, bankAccount.getBalance()); //larger amount
        assertEquals(70, bankAccount.getBalance());  //small amount
           

        //over draws(not enough money)
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(400000));


        //IllegalArgumentException 
        //assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-300));
        //assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0));


        
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
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}