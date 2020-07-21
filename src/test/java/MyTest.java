import com.lisz.proxy.CalculatorProxy;
import com.lisz.service.Calculator;
import com.lisz.service.MyCalculator;
import com.lisz.service.myinterface.MyInterface;
import com.lisz.service.myinterface.MySubclass;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MyTest {

	@Test
	public void test01() throws NoSuchMethodException {
		Calculator calculator = (Calculator) CalculatorProxy.getProxy(new MyCalculator());
		calculator.add(1, 2);
		calculator.sub(2, 3);
		calculator.mul(4, 5);
		calculator.div(10, 2);
		System.out.println(calculator.getClass());
		//CalculatorProxy.getProxy(new MySubclass());

		MyInterface myInterface = (MyInterface) CalculatorProxy.getProxy(new MySubclass());
		myInterface.show();
	}

	@Test
	public void test02() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		Calculator calculator = context.getBean("myCalculator", Calculator.class);
		int res = calculator.add(100, 20);
		System.out.println(res);
		System.out.println(calculator.sub(2, 3));
		System.out.println(calculator.getClass());
	}
}
