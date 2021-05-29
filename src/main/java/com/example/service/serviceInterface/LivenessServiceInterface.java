package com.example.service.serviceInterface;

import com.example.entity.Liveness;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LivenessServiceInterface {



    ///   20200525
    public Liveness enterLiveness(Liveness liveness) ;


    ///   20200425
    public Liveness Sel(int id) ;


    public void add_Liveness(Liveness liveness) ;

    public List<Liveness> SelByOnlineTime(Liveness liveness1);

    //20200702
    public void update_deadlineTime(Liveness liveness2) ;

    public List<Liveness> SelByUserId(Integer userId);

    // DarenSu   20200824
    public void delete_Liveness(Liveness liveness) ;


    //20201214
    public void updateTheLivenessid();



    //20201214
    public void resetTheLivenessid() ;
}
