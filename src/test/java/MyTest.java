import com.lisz.service.MyCaculator;
import org.junit.jupiter.api.Test;

public class MyTest {

	@Test
	public void test01() throws NoSuchMethodException {
		MyCaculator myCaculator = new MyCaculator();
		System.out.println(myCaculator.add(1, 2));
		System.out.println(myCaculator.div(1, 1));
	}
}
