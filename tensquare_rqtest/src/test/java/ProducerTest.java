import com.tensquare_rq.RQApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= RQApplication.class)
public class ProducerTest {

    @Autowired
    private RabbitMessagingTemplate template;

    /**
     * 直接模式
     */
    @Test
    public void sendMsg(){
        template.convertAndSend("","simon","直接模式");
    }

    /**
     * 分列模式
     */
    @Test
    public void sendMsg2(){
        template.convertAndSend("jiaohuanqi","","分列模式");
    }

    /**
     * 主题模式
     */
    @Test
    public void sendMsg3(){
        template.convertAndSend("topic_simon","good.log","主题模式");
    }


}
