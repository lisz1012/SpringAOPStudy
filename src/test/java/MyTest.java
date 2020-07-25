import com.lisz.proxy.CalculatorProxy;
import com.lisz.service.Calculator;
import com.lisz.service.MyCalculator;
import com.lisz.service.impl.MyCalculator2;
import com.lisz.service.myinterface.MyInterface;
import com.lisz.service.myinterface.MySubclass;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MyTest {
	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

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
		Calculator calculator = context.getBean("myCalculator", Calculator.class);
		int res = calculator.add(100, 20);
		System.out.println(res);
		System.out.println(calculator.sub(2, 3));
		System.out.println(calculator.getClass());
	}

	@Test
	public void test03() throws Exception {
		Calculator myCalculator2 = context.getBean("myCalculator2", Calculator.class);
		System.out.println(myCalculator2.add(1, 2));
	}

	@Test
	public void test04() throws Exception {
		Calculator myCalculator = context.getBean("myCalculator", Calculator.class);
		myCalculator.show(1);
	}
}
