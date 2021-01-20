package selenium.com;

public class Prime {

	public static void main(String[] args) {
		int n = 26, count = 0;
		for (int i = 2; i<=n/2;i++) {
			if(n%i==0) {
				count ++;
			}
			if(count>2) {
				break;
			}
		}
		System.out.println(count<2 ? "Number is prime": "Not prime");
	}

}
