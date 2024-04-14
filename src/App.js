
import React, { useState,useEffect } from 'react';
import MapComponent from './Components/MapComponent';
import EarthquakeInputComponent from './Components/EarthquakeInputComponent';
import './../node_modules/bootstrap/dist/css/bootstrap.min.css'
import EarthquakeGeneratorComponent from './Components/EarthquakeGeneratorComponent';
import axios from 'axios';
import { useInterval } from './hooks/useInterval';

const App = () => {
  const [earthquakes, setEarthquakes] = useState([]);

  const fetchData = () => {
    axios.get('http://localhost:8081/earthquake/get')
      .then(response => {
        console.log(response.data);
        setEarthquakes(response.data); 
      })
      .catch(error => {
        console.error('Error fetching earthquake data:', error);
      });
  };


  useInterval(()=>{
    fetchData()
  },15000)

  return (
    <div>
      <div className='container'>
        <h3 className='text-center my-4'>Deprem Haritası</h3>
        <EarthquakeInputComponent fetchData={fetchData} />
        <EarthquakeGeneratorComponent  fetchData={fetchData}/>
      </div>
      <MapComponent earthquakes={earthquakes}/>
    </div>
  );
};

export default App;
