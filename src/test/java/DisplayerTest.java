import com.lisz.displayer.Displayer;
import com.lisz.displayer.impl.DisplayerImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DisplayerTest {
	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	@Test
	public void test08() throws Exception {
		Displayer displayer = context.getBean("displayerImpl", Displayer.class); // IOC
		int res = displayer.display(3);
		//System.out.println("The final result is: " + res);
		System.out.println(displayer.getClass().getName());
		Assert.assertEquals(3, res);
	}

	@Test
	public void test09() throws Exception {
		Displayer displayer = context.getBean("displayerImpl", Displayer.class); // IOC
		displayer.print();
	}
}
