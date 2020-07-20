import com.lisz.proxy.CalculatorProxy;
import com.lisz.service.Calculator;
import com.lisz.service.MyCalculator;
import org.junit.jupiter.api.Test;


public class MyTest {

	@Test
	public void test01() throws NoSuchMethodException {
		Calculator calculator = CalculatorProxy.getProxy(new MyCalculator());
		calculator.add(1, 2);
		calculator.sub(2, 3);
		calculator.mul(4, 5);
		calculator.div(10, 2);
		System.out.println(calculator.getClass());
		//CalculatorProxy.getProxy(new MySubclass());
	}
}
