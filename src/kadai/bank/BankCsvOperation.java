package kadai.bank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BankCsvOperation {

	public static void main(String[] args) {

		System.out.println("銀行システム起動");

		ArrayList<Bank> bankList = new ArrayList<>();

		// 口座情報CSV読み込み
		try (BufferedReader accountReader = new BufferedReader(new FileReader("account.csv"))) {

			String line;

			while ((line = accountReader.readLine()) != null) {

				String[] data = line.split(",");

				String name = data[0];
				int balance = Integer.parseInt(data[1]);

				Bank bank = new Bank(name, balance);

				bankList.add(bank);

				System.out.println("口座番号：" + bank.getAccountNumber()
						+ " 口座名義人：" + bank.getName()
						+ " 初期残高：" + bank.getBalance());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("口座開設完了");

		// 取引情報CSV読み込み
		try (BufferedReader accountReader = new BufferedReader(new FileReader("transaction.csv"))) {

			String line;

			while ((line = accountReader.readLine()) != null) {

				String[] data = line.split(",");

				String accountNumber = data[0];
				String type = data[1];
				int amount = Integer.parseInt(data[2]);

				Bank target = null;

				for (Bank bank : bankList) {
					if (bank.getAccountNumber().equals(accountNumber)) {
						target = bank;
						break;
					}
				}

				if (target == null) {
					continue;
				}

				switch (type) {

				case "deposit":

					target.deposit(amount);

					System.out.println(accountNumber + "に入金しました。金額：" + amount);

					break;

				case "withdraw":

					Bank.Result result = target.withdraw(amount);

					if (result == Bank.Result.SUCCESS) {
						System.out.println(accountNumber + "から出金しました。金額：" + amount);
					} else {
						System.out.println(accountNumber + "は出金できませんでした。");
					}

					break;

				case "getBalance":

					System.out.println("残高照会（" + target.getName() + "）：" + target.getBalance() + "円");
					target.showHistory();

					break;

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("銀行システム停止");

	}

}