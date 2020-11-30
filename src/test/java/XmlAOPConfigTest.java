import com.lisz.service.Calculator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlAOPConfigTest {
	private ApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");
	@Test
	public void test01() throws Exception{
		Calculator myCalculator = context.getBean("myCalculator", Calculator.class);
		Object myCalculator1 = context.getBean("myCalculator");
		myCalculator.add(2,3);
	}
}
