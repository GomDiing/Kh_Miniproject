import styled from "styled-components"

const Foot = styled.div`
    text-align: center;
    color: white;
    background-color: #222831;
    margin-top: 30px;

p{
    font-size: 15px;
    margin: 0;
}
img{
    margin: 5px;
    width: 80px;
    height: 60px;
}
h2{
    font-size: 25px;
    color :#FFD369;
}
.logo {
    height: 80px;
    width: 85px;
}
`
const Footer = () => {
    return(
        <Foot>
            <img className="logo" src="/images/Logo.png" alt="Logo"/>
            <h2>Contact MovieScript</h2>
            <p>Main : <span>하정목</span></p>
            <p>Menu & Search : <span>김승렬</span></p>
            <p>DB & Server : <span>김성탁</span></p>
            <p>LogIn & SingUp : <span>박하린</span></p>
            <p>My Page : <span>지민</span></p>
            <br/>
        </Foot>
    );
}

export default Footer;