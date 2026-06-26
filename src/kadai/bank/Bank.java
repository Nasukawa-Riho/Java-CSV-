package kadai.bank;

import java.util.ArrayList;  //取引履歴//
import java.util.List;

public class Bank {
	
	private String name;
	private int balance;
	private String accountNumber;
	    
	//取引履歴//
	private List<Transaction> history = new ArrayList<>();
	    
	//口座番号自動生成//
	private static int nextAccountNumber = 1;
	    
	public Bank(String name, int initialBalance) {
			
		this.name = name;
		this.balance = initialBalance;
		
		//口座番号生成//
		this.accountNumber = String.format("%07d", nextAccountNumber++);
		history.add(new Transaction("初期預金", initialBalance, balance));
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public boolean deposit(int amount) {
		if(amount <= 0) {
			return false;
		}
		balance += amount;
		history.add(new Transaction("入金", amount, balance)); // ←入金履歴
		return true;
	}
	
	// 戻り値ありの「出金」メソッド。enumは「決まった選択肢だけを持てる型。正誤の原因がわかる。」
	public enum Result {
		SUCCESS,
		INVALID_AMOUNT,
		INSUFFICIENT_BALANCE
	}
		    
	public Result withdraw(int amount) {
		if (amount <= 0) {
			return Result.INVALID_AMOUNT;
		}
		
		if (balance < amount) {
			return Result.INSUFFICIENT_BALANCE;
		}

		balance -= amount;  //もとの残高が出金額(入力値)より多いor同じ場合　＝つまり出金しても残高がマイナスにならない//
		history.add(new Transaction("出金", amount, balance));
		return Result.SUCCESS;
	}
		    
	// 戻り値なしの「残高照会」メソッド
	public void showBalance() {
		System.out.println("残高は " + balance + " 円です");
	}
	//履歴表示メソッド//
	public void showHistory() {
		if(history.isEmpty()) {
			System.out.println("取引履歴はありません");
			return;
		}
				
		System.out.println("取引履歴（口座番号：" + accountNumber + "）");
		for(Transaction t : history) {
			t.show();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getBalance() {
		return balance;
	}
}