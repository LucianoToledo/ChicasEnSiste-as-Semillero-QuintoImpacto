import progressbar from "./progressbar.module.css";

const Progressbar = ({value, max}) => {
  return (
    <div className={progressbar.container}>
      <progress value={value} max={max} />
      <span>{(value/max) * 100}%</span>
    </div>
  )
}
export default Progressbar