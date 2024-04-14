// MapComponent.js
import React, { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import '../styles/MapComponent.css';
import L from 'leaflet';
import axios from 'axios';

const redCircleIcon = new L.Icon({
  iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41],
});

const formatDate = (dateTimeString) => {
  const dateTime = new Date(dateTimeString);
  return dateTime.toLocaleString();
};

const MapComponent = ({earthquakes}) => {

  return (
    <div className="mt-4">
      <MapContainer center={[0, 0]} zoom={2} style={{ height: '75vh', width: '100%' }}>
        <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
        {earthquakes?.map((quake, index) => (
          <Marker key={index} position={[quake.lat, quake.lon]} icon={redCircleIcon}>
            <Popup>
              Şiddet (Magnitude): {quake.mag} <br />
              Enlem (Latitude): {quake.lat} <br />
              Boylam (Longitude): {quake.lon} <br />
              Tarih (Date): {formatDate(quake.time)} <br />
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </div>
  );
};

export default MapComponent;
