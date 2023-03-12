import React from 'react';
import ReactPlayer from 'react-player';

const Video = ({video}) => {
  console.log(video)
  return (
    <div>
      <h3 className='font-sans font-medium text-[25px] text-dark-steal'>{video.name}</h3>
        <ReactPlayer 
            url={video.url}
            controls 
            loop 
            className="mt-[15px]" 
        /> 
    </div>
  )
}

export default Video