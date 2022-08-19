package com.aula.android.mapas

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import values.BitmapHelper

class MainActivity : AppCompatActivity() {

    // criando arrays de pins para mostrar no mapa
    private val places =
        arrayListOf(
            Place(
                "FIAP Campus Vila Olímpia",
                LatLng(-23.5955843, -46.6851937),
                "Rua Olimpíadas, 186 - São Paulo - SP",
                4.8f
            ),
            Place(
                "FIAP Campus Paulista",
                LatLng(-23.5643721, -46.6232043),
                "Av. Paulista, 1106 - São Paulo - SP",
                5.0f
            ),
            Place(
                "FIAP Campus Vila Mariana",
                LatLng(-23.5746685, -46.6232043),
                "Av. Lins de Vasconcelos, 1264 - São Paulo - SP",
                4.8f
            )
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // identificando no layout o fragment para injetar nele o mapa
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            // adicionando pins no mapa
            addMarkers(googleMap)

            // aqui estamos direcionando o mapa para abrir nos locais com pins
            googleMap.setOnMapLoadedCallback{
                val bounds = LatLngBounds.builder()
                places.forEach{
                    bounds.include(it.latLng)
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(),100))
            }
        }
    }

    private fun addMarkers(googleMap: GoogleMap) {
        // para cada item dentro do  arrays "places" adicionamos os pins no mapa
        places.forEach { place ->
            googleMap.addMarker(
                MarkerOptions().title(place.name).snippet(place.address).position(place.latLng)
                    .icon(
                        BitmapHelper.vectorToBitmap( //conversor de bitmap, aqui estamos definindo um pin estilizado
                            this,
                            R.drawable.ic_dog_foreground,
                            ContextCompat.getColor(this, R.color.purple_700)
                        )
                    )
            )
        }
    }

    // definição de uma classe chamada Place, o google maps já fornece ela,
    // porém aqui estamos sobrepondo a existente para colocar o rate
    data class Place(
        val name: String, val latLng: LatLng, val address: String, val rating: Float
    )
}