package ru.alexander.maptest

import io.reactivex.Observable


interface PositionApiServiceStub {


    /**
     *
     * Stub
    Latitude: 30°36′13″N   30.6037272
    Longitude: 125°24′55″W   -125.41521194
    Latitude: 52°21′23″N   52.35640627
    Longitude: 93°47′04″W   -93.78457115
    Latitude: 9°23′49″S   -9.39689803
    Longitude: 76°41′54″E   76.69835037
    Latitude: 65°42′38″S   -65.71048748
    Longitude: 57°40′30″W   -57.6749349
    Latitude: 5°25′34″N   5.42606616
    Longitude: 18°00′59″W   -18.01677698
    Latitude: 4°14′54″N   4.24820024
    Longitude: 97°00′22″W   -97.00608391
    Latitude: 71°53′30″N   71.89153184
    Longitude: 79°04′44″W   -79.07890195
    Latitude: 47°23′55″S   -47.39873374
    Longitude: 160°23′44″W   -160.3954842
    Latitude: 62°13′25″N   62.2235161
    Longitude: 7°03′03″E   7.05073901

     */

    fun search(): Observable<List<Point>>

    companion object Factory {
        fun create(): PositionApiServiceStub {
            return object : PositionApiServiceStub {
                override fun search(): Observable<List<Point>> = Observable.create {
                    val points = ArrayList<Point>()
                    points.add(Point(-125.41521194, 30.6037272))
                    points.add(Point(-93.78457115, 52.35640627))
                    points.add(Point(76.69835037, -9.39689803))
                    points.add(Point(-57.6749349, -65.71048748))
                    points.add(Point(-18.01677698, 5.42606616))
                    it.onNext(points)

                }

            }
        }
    }
}