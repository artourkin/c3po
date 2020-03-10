import React, { Component } from "react";
import { Bar, Line } from "react-chartjs-2";

class Histogram extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: null
    };
  }

  componentDidMount() {
    fetch("http://localhost:8081/rest/properties")
      .then(response => response.json())
      .then(data => this.setState({ data }));
  }
  render() {
    const { hits } = this.state;
    return <Bar data={this.state.data} height={70} />;
  }
}

export default Histogram;
