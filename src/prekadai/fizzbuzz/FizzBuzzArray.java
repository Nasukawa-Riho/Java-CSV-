package prekadai.fizzbuzz;

/**
 * プレ課題（FizzBuzzArray）
 * 
 * @author 
 * @version 1.0
 *
 */
public class FizzBuzzArray {

	/**
	 * (余裕あればメソッド化やクラスのインスタンス化してください)
	 * 1から100までの数を順番に表示し、結果を配列に格納するプログラムを作成してください。<br>
	 * ただし、以下の条件を満たす場合には特定の文字列を配列に格納します。<br>
	 * 3で割り切れる場合： “Fizz”<br>
	 * 5で割り切れる場合： “Buzz”<br>
	 * 3と5の両方で割り切れる場合： “FizzBuzz”<br>
	 * 例）1から15までの数を表示すると以下のようになります：<br>
	 * 1<br>
	 * 2<br>
	 * Fizz<br>
	 * 4<br>
	 * Buzz<br>
	 * Fizz<br>
	 * 7<br>
	 * 8<br>
	 * Fizz<br>
	 * Buzz<br>
	 * 11<br>
	 * Fizz<br>
	 * 13<br>
	 * 14<br>
	 * FizzBuzz<br>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		String[] numbers = new String[100];
		
		for (int i = 0; i < 100; i++) {	
			
			int value = i + 1;
			
			if(value % 15 == 0) {     //3と5の両方で割り切れる場合
				numbers[i] = "FizzBuzz";
			} else if (value % 3 == 0) {
				numbers[i] = "Fizz";   //3で割り切れる場合
			} else if (value % 5 == 0) {
				numbers[i] = "Buzz";   //5で割り切れる場合
			} else {              //それ以外（3でも５でも割り切れない場合）←これがないとnullになる
				numbers[i] = String.valueOf(value);  //String.valueOf(value)はvalue(値)を文字列に変換しての意味
			}
			
			System.out.println(numbers[i]);
		    
		}
	}
}