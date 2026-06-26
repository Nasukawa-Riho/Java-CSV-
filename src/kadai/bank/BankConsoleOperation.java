package kadai.bank;
/**
 * 銀行システムのコンソール操作を提供するクラスです。
 *
 * @author 
 * @version 1.0
 */


import java.util.Scanner;

public class BankConsoleOperation {
    public static Bank findAccount(Bank[] accounts, String accountNumber) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)){
            	return accounts[i];  //見つけたら即返す//
            }
        }
        return null;    //見つからなかった場合//
    }
    
    //入力値の定義についてメソッド化しておくと楽//
    public static int inputInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("数値を入力してください");
            }
        }
    }
    
    //入金時専用メソッド//
    public static int inputDepositAmount(Scanner scanner) {
        while (true) {
            System.out.print("入金額を入力してください（1円以上、キャンセルする場合は0を入力）：");
            String input = scanner.nextLine();

            try {
                int amount = Integer.parseInt(input);

                if (amount == 0) {
                    System.out.println("メニューに戻ります。");
                    return -1;  // メニューに戻るための特別値
                }

                if (amount < 0) {
                    System.out.println("金額は正の数で入力してください");
                    continue;  // 再入力
                }

                return amount; // 正の数なら返す

            } catch (NumberFormatException e) {
                System.out.println("数値を入力してください");
            }
        }
    }
    
    //出金専用メソッド//
    public static int inputWithdrawAmount(Scanner scanner) {
        while (true) {
            System.out.print("出金額を入力してください（1円以上、キャンセルする場合は0を入力）：");
            String input = scanner.nextLine();
            try {
                int amount = Integer.parseInt(input);
                if (amount == 0) {
                    System.out.println("メニューに戻ります。");
                    return -1; // キャンセル
                }
                if (amount < 0) {
                    System.out.println("金額は正の数で入力してください");
                    continue;
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("数値を入力してください");
            }
        }
    }
    
    //入出金の入力額のメソッド化ここまで//
	
	/**
	 * メインメソッド。<br>
	 * 銀行システムを起動し、口座開設、入金、出金、残高照会、終了の操作を提供します。
	 *
	 */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("銀行システム起動");
		System.out.print("口座開設する人数を入力してください："); //lnは改行なのでなくす！
		 
        //口座を人数分開設する
        int number;

        while (true) {
            number = inputInt(scanner);   // コンソールから入力された値を数値変換//
            if (number <= 0) {
                System.out.println("1人以上入力してください");
                continue;
            }
            //テスト後勝手に追加//
            if (number > 1000) {
                System.out.println("人数が多すぎます（最大1000人まで）");
                continue;
            }
            
            break;
        }
        
        //新しい口座の箱を作成//
        Bank[] accounts = new Bank[number]; 
        
        //口座について
        for(int i = 0; i < number; i++) {
        	//口座名義人の入力に制限をかける//
        	String name;

        	while (true) {
        	    System.out.print((i+1) + "人目の口座名義人を入力してください：");
        	    name = scanner.nextLine();

        	    // 前後スペース除去
        	    name = name.trim();

        	    if (name.isEmpty()) {
        	        System.out.println("名前を入力してください");
        	        continue;
        	    }

        	    if (name.length() > 20) {
        	        System.out.println("名前は20文字以内で入力してください");
        	        continue;
        	    }

        	    // 記号チェック（日本語・英字のみOK）
        	    if (!name.matches("^[ぁ-んァ-ン一-龥a-zA-Z]+$")) {
        	        System.out.println("名前は文字のみで入力してください");
        	        continue;
        	    }

        	    break;
        	}
        	
        	//負の数の初期残高にならないようにする//
        	int initialBalance;

            while (true) {
                System.out.print("初期残高を入力してください：");
                initialBalance = inputInt(scanner);

                if (initialBalance < 0) {
                    System.out.println("0以上を入力してください");
                    continue;
                }

                if (initialBalance > 10000000) {
                    System.out.println("初期残高は1000万円以下にしてください");
                    continue;
                }
                break;
            }
        	
            //開設した口座（口座名義と初期残高の情報をもつ）//
            accounts[i] = new Bank(name, initialBalance);

            System.out.println(
                "口座作成完了：" + name + "さんの口座番号：" 
                + accounts[i].getAccountNumber()
            );  //ここ追加したので忘れない！最後に口座番号も確認できるとテストしやすい//
        }
        
        
        while (true) {
        	// メニュー表示
        	System.out.println("操作を選択してください");
        	System.out.println("1: 入金");
        	System.out.println("2: 出金");
        	System.out.println("3: 残高照会");
        	System.out.println("4: 終了");
        	System.out.print("選択:");
        	
        	// ↑の選択についてchoiceとして入力を受け取る(数値入力のみ可のメソッド使用)
        	int choice = inputInt(scanner);
            
            //入力値の範囲が正しいかチェック//
            if (choice < 1 || choice > 4) {
                System.out.println("1〜4を選択してください");
                continue;
            }
            
            //正しく受け取ったら、受け取った内容で分岐する//
            //下記はswitch文の中で繰り返し使う変数だからswitch外で宣言！//
            String accountNumber;
            Bank target;
            
            switch(choice) {
               case 1:    //入金//
            	   System.out.print("口座番号を入力してください：");
                   //入力値のメソッド使用//
            	   accountNumber = scanner.nextLine();
            	
                   //繰り返し出てくるので冒頭でメソッド化していた「口座検索」の呼び出し。検索結果を入れるために用意した新たな箱にin。//
            	   target = findAccount(accounts, accountNumber);
                
                //アドバイス通りに、入出金の入力額について事前にメソッド化しておいたものを使用//
                //口座が存在するかのnullチェックも//
            	   if(target == null) {
            		   System.out.println("口座が見つかりません");
            		   break;
            	   }
            	   
            	   int amount = inputDepositAmount(scanner); // ←入金専用メソッドを呼ぶ
            	   if (amount == -1) break;  //キャンセル
            	   
            	   //当初Bankクラスで表示していたものを指摘後分離//
            	   if(target.deposit(amount)) {
            		    System.out.println(amount + "円入金しました");
            		} else {
            		    System.out.println("不正な金額です");
            		}
            	   
            	   target.showBalance();        // 入金後の残高表示
            	   target.showHistory();        // ←そのまま

            	   System.out.println("Enterを押すとメニューに戻ります...");
            	   scanner.nextLine();
            	   break;
            	   
               case 2:    //出金//
            	   System.out.print("口座番号を入力してください：");
            	   accountNumber = scanner.nextLine();
            	
                   //繰り返し出てくるので冒頭でメソッド化していた「口座検索」の呼び出し。検索結果を入れるために用意した新たな箱にin。//
            	   target = findAccount(accounts, accountNumber);
            	
                   //アドバイス通りに、入出金の入力額について事前にメソッド化しておいたものを使用//
                   //口座が存在するかのnullチェックも//
            	   if(target == null) {
            		   System.out.println("口座が見つかりません");
            		   break;
            	   }
            	   
            	   int withdrawAmount = inputWithdrawAmount(scanner); // ←出金専用メソッド
            	   if (withdrawAmount == -1) break; // キャンセル
            	   
            	   //当初Bankクラスで表示していたものを指摘後分離//
            	   Bank.Result result = target.withdraw(withdrawAmount);

            	   switch(result) {
            	       case SUCCESS:
            	           System.out.println(withdrawAmount + "円出金しました");
            	           break;
            	       case INVALID_AMOUNT:
            	           System.out.println("不正な金額です");
            	           break;
            	       case INSUFFICIENT_BALANCE:
            	           System.out.println("残高が不足しています");
            	           break;
            	   } //Bankクラスにて出金額と元の残高を比較して出金後の残高がマイナスにならないようにする//
            	   
            		   
            	   target.showBalance();     // 出金後の残高表示(親切追加)
            	   target.showHistory();         // 履歴表示 ←追加
            	   System.out.println("Enterを押すとメニューに戻ります...");
            	   scanner.nextLine(); // ←追加！！
            	   break;
            	
               case 3:    //残高照会・取引履歴//
            	   System.out.print("口座番号を入力してください：");
            	   accountNumber = scanner.nextLine();
            	
                   //繰り返し出てくるので冒頭でメソッド化していた「口座検索」の呼び出し。検索結果を入れるために用意した新たな箱にin。//
            	   target = findAccount(accounts, accountNumber);

            	   //nullチェック//
            	   if (target == null) {
            		   System.out.println("口座が見つかりません");
            		   break;
            	   }
            	   
            	   target.showBalance();
            	   target.showHistory();
            	   System.out.println("Enterを押すとメニューに戻ります...");
            	   scanner.nextLine(); // ←追加！！
            	   break;
            	   
               case 4:    //終了//case 4:
            	    scanner.close();
            	    System.out.println("銀行システム停止");
            	    return;
             } //switch終了//
            }  //while終了//
        }  //main終了//
	}  //クラス終了//