package de.tu_darmstadt.kom.freifunkfinder.common.converter;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDAL;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

/**
 * Created by govind on 12/17/2015.
 */
public class DTOAndDALConverter implements ConverterInt<WifiAccessPointDTO, WifiAccessPointDAL> {

    private static DTOAndDALConverter dtoAndDALConverter = null;

    // singleton converter
    private DTOAndDALConverter(){

    }

    public static DTOAndDALConverter geDtoAndDALConverter(){
        if(dtoAndDALConverter == null){
            dtoAndDALConverter = new DTOAndDALConverter();
        }
        return dtoAndDALConverter;
    }

    @Override
    public WifiAccessPointDAL serialize(WifiAccessPointDTO wifiAccessPointDTO) throws Exception {
        WifiAccessPointDAL wifiAccessPointDAL = new WifiAccessPointDAL();
        wifiAccessPointDAL.setNodeId(wifiAccessPointDTO.getNodeId());
        wifiAccessPointDAL.setHostName(wifiAccessPointDTO.getHostName());
        //wifiAccessPointDAL.setLatitude(wifiAccessPointDTO.getLatitude());
        //wifiAccessPointDAL.setLongitude(wifiAccessPointDTO.getLongitude());
        //wifiAccessPointDAL.setAltitude(wifiAccessPointDTO.getAltitude());
        return wifiAccessPointDAL;
    }

    @Override
    public WifiAccessPointDTO deSerialize(WifiAccessPointDAL wifiAccessPointDAL) throws Exception {
        WifiAccessPointDTO wifiAccessPointDTO = new WifiAccessPointDTO();
        wifiAccessPointDTO.setNodeId(wifiAccessPointDAL.getNodeId());
        wifiAccessPointDTO.setHostName(wifiAccessPointDAL.getHostName());
        //wifiAccessPointDTO.setLatitude(wifiAccessPointDAL.getLatitude());
        //wifiAccessPointDTO.setLongitude(wifiAccessPointDAL.getLongitude());
        //wifiAccessPointDTO.setAltitude(wifiAccessPointDAL.getAltitude());
        return wifiAccessPointDTO;
    }
}
