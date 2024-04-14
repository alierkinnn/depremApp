// EarthquakeInputComponent.js
import React, { useState } from 'react';
import axios from 'axios';
import {  ToastContainer,toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const EarthquakeInputComponent = ({ fetchData }) => {
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const [magnitude, setMagnitude] = useState('');

  const createEarthquake = async (event) => {
    event.preventDefault();
    if (!latitude || !longitude || !magnitude){
      toast.error('Lütfen eksik değer girmeyiniz !', {
        position: "top-right",
        autoClose: 2500,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "dark",
        });
        resetForm();
      return;
    }

    else if ( Math.abs(latitude) > 90 || Math.abs(longitude) > 180) {
      toast.error('Lütfen geçerli enlem ve boylam değerleri giriniz !', {
        position: "top-right",
        autoClose: 2500,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "dark",
        });
        resetForm();
      return;
    }

    else if (magnitude >= 13) {
      toast.error('Lütfen geçerli bir şiddet değeri giriniz !', {
        position: "top-right",
        autoClose: 2500,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "dark",
        });
        resetForm();
      return;
    }

    const newEarthquake = {
      lat: parseFloat(latitude),
      lon: parseFloat(longitude),
      mag: parseFloat(magnitude)
    };

    try {
      const response = await axios.post('http://localhost:8081/earthquake/add', newEarthquake);
      console.log(response.data);
      await fetchData(); 
      toast.success('Deprem başarıyla sisteme eklenmiştir !', {
        position: "top-right",
        autoClose: 2500,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "dark",
        });
    } catch (error) {
      console.error('Failed to add earthquake:', error);
    }

    resetForm();
  };

  const resetForm = () =>{
    setLatitude('');
    setLongitude('');
    setMagnitude('');
  }

  return (
    <div className='d-flex justify-content-center mt-4'>
      <div className='mx-2 mt-1'>
        <label className='mx-2'>Enlem (Latitude):</label>
        <input type="number" value={latitude} onChange={(e) => setLatitude(e.target.value)} step="0.0001" required />
      </div>
      <div className='mx-2 mt-1'>
        <label className='mx-2'>Boylam (Longitude):</label>
        <input type="number" value={longitude} onChange={(e) => setLongitude(e.target.value)} step="0.0001" required />
      </div>
      <div className='mx-2 mt-1'>
        <label className='mx-2'>Şiddet (Magnitude):</label>
        <input type="number" value={magnitude} onChange={(e) => setMagnitude(e.target.value)} step="0.1" required />
      </div>
      <button onClick={createEarthquake} className='mx-2 btn btn-primary'>Ekle</button>
      
      <ToastContainer />
    </div>
  );
};

export default EarthquakeInputComponent;
