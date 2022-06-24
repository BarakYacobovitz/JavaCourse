package exceptions;

/**
 * Created by kobis on 15 May, 2022 - exception handle
 */
public class CouponsSystemException extends Exception{

    public CouponsSystemException(String msg){
        super(msg);
    }

    public CouponsSystemException(LayerMsg layerMsg){
        super(layerMsg.getMsg());
    }
}
