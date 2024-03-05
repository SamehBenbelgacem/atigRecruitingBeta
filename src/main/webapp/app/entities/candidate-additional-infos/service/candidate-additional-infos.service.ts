import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICandidateAdditionalInfos, NewCandidateAdditionalInfos } from '../candidate-additional-infos.model';

export type PartialUpdateCandidateAdditionalInfos = Partial<ICandidateAdditionalInfos> & Pick<ICandidateAdditionalInfos, 'id'>;

type RestOf<T extends ICandidateAdditionalInfos | NewCandidateAdditionalInfos> = Omit<T, 'birthday' | 'firstContact'> & {
  birthday?: string | null;
  firstContact?: string | null;
};

export type RestCandidateAdditionalInfos = RestOf<ICandidateAdditionalInfos>;

export type NewRestCandidateAdditionalInfos = RestOf<NewCandidateAdditionalInfos>;

export type PartialUpdateRestCandidateAdditionalInfos = RestOf<PartialUpdateCandidateAdditionalInfos>;

export type EntityResponseType = HttpResponse<ICandidateAdditionalInfos>;
export type EntityArrayResponseType = HttpResponse<ICandidateAdditionalInfos[]>;

@Injectable({ providedIn: 'root' })
export class CandidateAdditionalInfosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/candidate-additional-infos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(candidateAdditionalInfos: NewCandidateAdditionalInfos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidateAdditionalInfos);
    return this.http
      .post<RestCandidateAdditionalInfos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(candidateAdditionalInfos: ICandidateAdditionalInfos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidateAdditionalInfos);
    return this.http
      .put<RestCandidateAdditionalInfos>(
        `${this.resourceUrl}/${this.getCandidateAdditionalInfosIdentifier(candidateAdditionalInfos)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(candidateAdditionalInfos: PartialUpdateCandidateAdditionalInfos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidateAdditionalInfos);
    return this.http
      .patch<RestCandidateAdditionalInfos>(
        `${this.resourceUrl}/${this.getCandidateAdditionalInfosIdentifier(candidateAdditionalInfos)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCandidateAdditionalInfos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCandidateAdditionalInfos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCandidateAdditionalInfosIdentifier(candidateAdditionalInfos: Pick<ICandidateAdditionalInfos, 'id'>): number {
    return candidateAdditionalInfos.id;
  }

  compareCandidateAdditionalInfos(
    o1: Pick<ICandidateAdditionalInfos, 'id'> | null,
    o2: Pick<ICandidateAdditionalInfos, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getCandidateAdditionalInfosIdentifier(o1) === this.getCandidateAdditionalInfosIdentifier(o2) : o1 === o2;
  }

  addCandidateAdditionalInfosToCollectionIfMissing<Type extends Pick<ICandidateAdditionalInfos, 'id'>>(
    candidateAdditionalInfosCollection: Type[],
    ...candidateAdditionalInfosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const candidateAdditionalInfos: Type[] = candidateAdditionalInfosToCheck.filter(isPresent);
    if (candidateAdditionalInfos.length > 0) {
      const candidateAdditionalInfosCollectionIdentifiers = candidateAdditionalInfosCollection.map(
        candidateAdditionalInfosItem => this.getCandidateAdditionalInfosIdentifier(candidateAdditionalInfosItem)!,
      );
      const candidateAdditionalInfosToAdd = candidateAdditionalInfos.filter(candidateAdditionalInfosItem => {
        const candidateAdditionalInfosIdentifier = this.getCandidateAdditionalInfosIdentifier(candidateAdditionalInfosItem);
        if (candidateAdditionalInfosCollectionIdentifiers.includes(candidateAdditionalInfosIdentifier)) {
          return false;
        }
        candidateAdditionalInfosCollectionIdentifiers.push(candidateAdditionalInfosIdentifier);
        return true;
      });
      return [...candidateAdditionalInfosToAdd, ...candidateAdditionalInfosCollection];
    }
    return candidateAdditionalInfosCollection;
  }

  protected convertDateFromClient<
    T extends ICandidateAdditionalInfos | NewCandidateAdditionalInfos | PartialUpdateCandidateAdditionalInfos,
  >(candidateAdditionalInfos: T): RestOf<T> {
    return {
      ...candidateAdditionalInfos,
      birthday: candidateAdditionalInfos.birthday?.toJSON() ?? null,
      firstContact: candidateAdditionalInfos.firstContact?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCandidateAdditionalInfos: RestCandidateAdditionalInfos): ICandidateAdditionalInfos {
    return {
      ...restCandidateAdditionalInfos,
      birthday: restCandidateAdditionalInfos.birthday ? dayjs(restCandidateAdditionalInfos.birthday) : undefined,
      firstContact: restCandidateAdditionalInfos.firstContact ? dayjs(restCandidateAdditionalInfos.firstContact) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCandidateAdditionalInfos>): HttpResponse<ICandidateAdditionalInfos> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCandidateAdditionalInfos[]>): HttpResponse<ICandidateAdditionalInfos[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
