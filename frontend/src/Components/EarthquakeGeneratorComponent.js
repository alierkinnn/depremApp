import React, { useState, useEffect } from 'react';
import { ClipLoader } from 'react-spinners';
import axios from 'axios';
import { useInterval } from '../hooks/useInterval';

const EarthquakeGeneratorComponent = ({ fetchData }) => {
  const [isGenerating, setIsGenerating] = useState(false);

const generate = ()=>{
  const earthquake = {
    lat: getRandomInRange(-90, 90, 3),
    lon: getRandomInRange(-180, 180, 3),
    mag: getRandomInRange(4, 10, 1)
  };

  axios.post('http://localhost:8081/earthquake/add', earthquake)
    .then(response => {
      console.log('Earthquake sent:', response.data);
    })
    .catch(error => {
      console.error('Failed to send earthquake:', error);
    });
}

  useInterval(()=>{   
    if (isGenerating) { 
        generate()
    } else {
      setIsGenerating(false);
      fetchData()
    }
  },3000)


   
  const toggleGenerating = () => {
   
    setIsGenerating(prevState => {
      if(!prevState){
        generate()
      }

      return !prevState});
  };

  return (
    <div className='d-flex justify-content-center mt-4'>

      <button className={`mx-2 btn ${isGenerating ? 'btn-danger' : 'btn-success'}`} onClick={toggleGenerating}>
        {isGenerating ? 'Stop Generating' : 'Start Generating'}
      </button>

      <div className="mx-2">
        <ClipLoader loading={isGenerating} size={32} />
      </div>

    </div>
  );
};

const getRandomInRange = (min, max, decimalPlaces) => {
  const num = Math.random() * (max - min) + min;
  return parseFloat(num.toFixed(decimalPlaces));
};

export default EarthquakeGeneratorComponent;
