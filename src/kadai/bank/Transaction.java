package kadai.bank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDateTime date; // 日付
    private String type;        // 入金 or 出金
    private int amount;         // 金額
    private int balance;        // 取引後残高

    public Transaction(String type, int amount, int balance) {
        this.date = LocalDateTime.now(); // 今の日時
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public void show() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(
            "日付：" + date.format(formatter)
            + " 取引種類：" + type
            + " 金額：" + amount + "円"
            + " 残高：" + balance + "円"
        );
    }
}