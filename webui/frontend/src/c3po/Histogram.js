import React, { Component } from "react";
import { Bar, Line } from "react-chartjs-2";
import {CustomTooltips} from "@coreui/coreui-plugin-chartjs-custom-tooltips";

const bar = {
  labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
  datasets: [
    {
      label: 'My First dataset',
      backgroundColor: 'rgba(255,99,132,0.2)',
      borderColor: 'rgba(255,99,132,1)',
      borderWidth: 1,
      hoverBackgroundColor: 'rgba(255,99,132,0.4)',
      hoverBorderColor: 'rgba(255,99,132,1)',
      data: [65, 59, 80, 81, 56, 55, 40],
    },
  ],
};
const options = {
  tooltips: {
    enabled: false,
    custom: CustomTooltips
  },
  maintainAspectRatio: true,
  onClick: f
}

function f(event, array) {
  console.log("WOW CLICK");
  console.log(event);
  console.log(array);
  alert(array[0]._index);
}

const cardChartOpts4 = {
  tooltips: {
    enabled: false,
    custom: CustomTooltips
  },
  maintainAspectRatio: false,
  legend: {
    display: false,
  },
  scales: {
    xAxes: [
      {
        display: true,
        barPercentage: 0.6,
      }],
    yAxes: [
      {
        display: true,
      }],
  },
};

class Histogram extends Component {


  constructor(props) {
    super(props);
    this.state = {
      data: bar
    };
  }

  componentDidMount() {
    fetch("http://localhost:8081/rest/properties")
      .then(response => response.json())
      .then(data =>

      {
        console.log(data);
        data = this.transform(data);
        this.setState({ data })
      });
  }

  transform(data) {
    //bar.labels = data.
    bar.datasets[0].data = data.map(item =>item.count);
    bar.data = data.map(item =>item.count);
    bar.labels=data.map(item =>item.property);
    return bar;
  }

  render() {
    const { hits } = this.state;
    return <Bar data={bar} options={options}/>;
  }
}

export default Histogram;
