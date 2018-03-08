package modules;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public List<String> startLocationTypes;
    public String travelMode;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
