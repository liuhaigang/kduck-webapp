package cn.kduck.assembler;

public class AssemblerContext {

    private ThreadLocal<Integer> failConutThreadLocal = new ThreadLocal();

    private AssemblerContext(){}

    public void setAuthFailConut(int failConut){
        failConutThreadLocal.set(failConut);
    }
}
