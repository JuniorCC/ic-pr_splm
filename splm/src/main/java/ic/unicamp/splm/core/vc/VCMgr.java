package ic.unicamp.splm.core.vc;

import java.util.Hashtable;
import java.util.LinkedList;

public class VCMgr{
    Hashtable<String, VCManager> vcList = new Hashtable<>();
    public void createVC(String name, VCManager vcManager){
        vcList.put(name,vcManager);
    }

    public VCManager getVC(String name){
        return vcList.get(name);
    }

    public LinkedList<String> getVCList(){
        return new LinkedList<>(vcList.keySet());
    }

}
