import com.briup.smart.implement.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: PACKAGE_NAME
 * @Author: Fz
 * @CreateTime: 2024-09-24  11:47
 * @Description: TODO
 * @Version: 1.0
 */
public class Sever {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket sever = new ServerSocket(8888);
        Socket accept = sever.accept();
        Thread  t1= new Sever_Thread("1", accept);
        t1.start();
    }
}
class Sever_Thread extends Thread {
    private Socket socket;
    public Sever_Thread(String name,Socket socket){
        super(name);
        this.socket = socket;
    }
    @Override
    public void run() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            Object o = ois.readObject();
            List<Student> list = new ArrayList<>();
            if(o instanceof List){
                List<?> l =(List<?>)o;
                for(Object object : l){
                    if(object instanceof  Student){
                        list.add((Student) object);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
