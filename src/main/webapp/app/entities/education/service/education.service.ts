import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEducation, NewEducation } from '../education.model';

export type PartialUpdateEducation = Partial<IEducation> & Pick<IEducation, 'id'>;

type RestOf<T extends IEducation | NewEducation> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

export type RestEducation = RestOf<IEducation>;

export type NewRestEducation = RestOf<NewEducation>;

export type PartialUpdateRestEducation = RestOf<PartialUpdateEducation>;

export type EntityResponseType = HttpResponse<IEducation>;
export type EntityArrayResponseType = HttpResponse<IEducation[]>;

@Injectable({ providedIn: 'root' })
export class EducationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/educations');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(education: NewEducation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(education);
    return this.http
      .post<RestEducation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(education: IEducation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(education);
    return this.http
      .put<RestEducation>(`${this.resourceUrl}/${this.getEducationIdentifier(education)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(education: PartialUpdateEducation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(education);
    return this.http
      .patch<RestEducation>(`${this.resourceUrl}/${this.getEducationIdentifier(education)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEducation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEducation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEducationIdentifier(education: Pick<IEducation, 'id'>): number {
    return education.id;
  }

  compareEducation(o1: Pick<IEducation, 'id'> | null, o2: Pick<IEducation, 'id'> | null): boolean {
    return o1 && o2 ? this.getEducationIdentifier(o1) === this.getEducationIdentifier(o2) : o1 === o2;
  }

  addEducationToCollectionIfMissing<Type extends Pick<IEducation, 'id'>>(
    educationCollection: Type[],
    ...educationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const educations: Type[] = educationsToCheck.filter(isPresent);
    if (educations.length > 0) {
      const educationCollectionIdentifiers = educationCollection.map(educationItem => this.getEducationIdentifier(educationItem)!);
      const educationsToAdd = educations.filter(educationItem => {
        const educationIdentifier = this.getEducationIdentifier(educationItem);
        if (educationCollectionIdentifiers.includes(educationIdentifier)) {
          return false;
        }
        educationCollectionIdentifiers.push(educationIdentifier);
        return true;
      });
      return [...educationsToAdd, ...educationCollection];
    }
    return educationCollection;
  }

  protected convertDateFromClient<T extends IEducation | NewEducation | PartialUpdateEducation>(education: T): RestOf<T> {
    return {
      ...education,
      startDate: education.startDate?.format(DATE_FORMAT) ?? null,
      endDate: education.endDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restEducation: RestEducation): IEducation {
    return {
      ...restEducation,
      startDate: restEducation.startDate ? dayjs(restEducation.startDate) : undefined,
      endDate: restEducation.endDate ? dayjs(restEducation.endDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEducation>): HttpResponse<IEducation> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEducation[]>): HttpResponse<IEducation[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
