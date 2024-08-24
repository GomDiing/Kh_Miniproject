import React from 'react'

function Genres(props) {
  console.log(props.genre)
  return (
    <>
    <div style={{display: 'inline-block'}} >
      <pre style={{color: 'black', fontSize: '1.5em', backgroundColor: '#FFD369', borderRadius: '20px 30px 20px 30px'}}> #{props.genre.name} </pre>
    </div>
    </>
  )
}

export default Genres