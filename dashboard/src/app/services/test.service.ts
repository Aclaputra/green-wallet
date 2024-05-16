import { Injectable } from '@angular/core';
import { History } from '../models/History';


@Injectable({
  providedIn: 'root'
})
export class TestService {

  constructor() { }
  project: History[] = [
    {
      id: 1,
      name: 'History1',
      description: 'Sky Book Application',
      link: 'https://github.com/MuhamadFaheem/sky-book-application',
    },
    {
      id: 2,
      name: 'History2',
      description: 'Desc history 2',
      link: 'https://github.com/MuhamadFaheem/TA-201943502149',
    },
    {
      id: 3,
      name: 'History 3',
      description: 'Desc History 3',
      link: 'https://github.com/MuhamadFaheem/analisis-covid-capstone',
    },
    {
      id: 4,
      name: 'History 4',
      description: 'Desc History 4',
      link: 'https://github.com/MuhamadFaheem/analisis-covid-capstone',
    },
    {
      id: 5,
      name: 'History 5',
      description: 'Desc History 5',
      link: 'https://github.com/MuhamadFaheem/analisis-covid-capstone',
    },
    {
      id: 6,
      name: 'History 6',
      description: 'Desc histoy 5',  
      link: 'https://github.com/MuhamadFaheem/analisis-covid-capstone',
    }
  ]

  getProjects(): History[] {
    return this.project
  }
  getProject(id: number): History{
    return this.project.find(project => project.id === id)!
  }
}
