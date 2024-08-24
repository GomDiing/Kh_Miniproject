import React from 'react';

// 상세페이지 상단 부분 가로이미지
function DetailImage(props) {
    return (
        <div style={{background: `black`, height: '700px', backgroundSize: 'cover', backgroundPosition: '100% 100%', 
            backgroundRepeat: 'no-repeat', width: '1280px', position: 'relative', backgroundImage:`url('${props.image}')`}}>
            <div style={{position: 'absolute', maxWidth: '700px', bottom: '1.2rem', marginLeft: '2rem'}}>
                <h2 style={{color: '#FFD669', fontSize: '3rem'}}>{props.title}</h2>
            </div>
        </div>
    );
}

export default DetailImage;