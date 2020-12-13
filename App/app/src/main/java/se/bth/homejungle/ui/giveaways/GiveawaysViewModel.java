package se.bth.homejungle.ui.giveaways;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import se.bth.homejungle.firestore.MarketplacePlantLiveData;
import se.bth.homejungle.firestore.MarketplacePlantRepository;
import se.bth.homejungle.storage.entity.Plant;
import se.bth.homejungle.ui.MarketplacePlant;

public class GiveawaysViewModel extends AndroidViewModel {
    MarketplacePlantRepository marketplacePlantRepository;
    MarketplacePlantLiveData liveData = new MarketplacePlantLiveData(null);
    public MutableLiveData<List<MarketplacePlant>> marketplacePlantsList = new MutableLiveData<List<MarketplacePlant>>();

    public GiveawaysViewModel(Application application){
        super(application);
        marketplacePlantRepository = new MarketplacePlantRepository();
    }

    public LiveData<List<MarketplacePlant>> getOwnGiveawaysLiveData(String userid){
        liveData = marketplacePlantRepository.getOwnGiveawaysLiveData(userid);
        return liveData;
    }

    public LiveData<List<MarketplacePlant>> getOwnGiveaways() {
        return liveData.marketplacePlantsList;
    }


    public void insert(Plant plant){
        //TODO: insert plant in database + change entity-import
    }

    public void delete(String docid){
        System.out.println("docid: " + docid);
        marketplacePlantRepository.deleteGiveaway(docid);
    }
}