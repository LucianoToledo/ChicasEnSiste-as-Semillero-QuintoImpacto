import iconInstagram from "../../assets/images/icons/instagram.png";
import iconLinkedin from "../../assets/images/icons/linkedin.png";
import logoFooter from "../../assets/images/logo-footer.png"; 

const Footer = () => {
  return (
    <footer className="w-full flex justify-between items-center">
      <div className="-translate-y-8">
        <img src={logoFooter} alt="logo" height="298" width="298" /> 
      </div>
      <div className="flex justify-evenly min-w-[150px]">
        <a target="_blank" rel="noreferrer noopener" href="https://www.instagram.com">
          <img src={iconInstagram} alt="instagram" height="50" width="50" />
        </a>
        <a target="_blank" rel="noreferrer noopener" href="https://ar.linkedin.com/">
          <img src={iconLinkedin} alt="linkedin" height="50" width="50" />
        </a>
      </div>
    </footer>
  );
};
export default Footer;