import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubEmail, NewSubEmail } from '../sub-email.model';

export type PartialUpdateSubEmail = Partial<ISubEmail> & Pick<ISubEmail, 'id'>;

type RestOf<T extends ISubEmail | NewSubEmail> = Omit<T, 'date' | 'snoozedTo'> & {
  date?: string | null;
  snoozedTo?: string | null;
};

export type RestSubEmail = RestOf<ISubEmail>;

export type NewRestSubEmail = RestOf<NewSubEmail>;

export type PartialUpdateRestSubEmail = RestOf<PartialUpdateSubEmail>;

export type EntityResponseType = HttpResponse<ISubEmail>;
export type EntityArrayResponseType = HttpResponse<ISubEmail[]>;

@Injectable({ providedIn: 'root' })
export class SubEmailService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sub-emails');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(subEmail: NewSubEmail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subEmail);
    return this.http
      .post<RestSubEmail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(subEmail: ISubEmail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subEmail);
    return this.http
      .put<RestSubEmail>(`${this.resourceUrl}/${this.getSubEmailIdentifier(subEmail)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(subEmail: PartialUpdateSubEmail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subEmail);
    return this.http
      .patch<RestSubEmail>(`${this.resourceUrl}/${this.getSubEmailIdentifier(subEmail)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSubEmail>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSubEmail[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSubEmailIdentifier(subEmail: Pick<ISubEmail, 'id'>): number {
    return subEmail.id;
  }

  compareSubEmail(o1: Pick<ISubEmail, 'id'> | null, o2: Pick<ISubEmail, 'id'> | null): boolean {
    return o1 && o2 ? this.getSubEmailIdentifier(o1) === this.getSubEmailIdentifier(o2) : o1 === o2;
  }

  addSubEmailToCollectionIfMissing<Type extends Pick<ISubEmail, 'id'>>(
    subEmailCollection: Type[],
    ...subEmailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const subEmails: Type[] = subEmailsToCheck.filter(isPresent);
    if (subEmails.length > 0) {
      const subEmailCollectionIdentifiers = subEmailCollection.map(subEmailItem => this.getSubEmailIdentifier(subEmailItem)!);
      const subEmailsToAdd = subEmails.filter(subEmailItem => {
        const subEmailIdentifier = this.getSubEmailIdentifier(subEmailItem);
        if (subEmailCollectionIdentifiers.includes(subEmailIdentifier)) {
          return false;
        }
        subEmailCollectionIdentifiers.push(subEmailIdentifier);
        return true;
      });
      return [...subEmailsToAdd, ...subEmailCollection];
    }
    return subEmailCollection;
  }

  protected convertDateFromClient<T extends ISubEmail | NewSubEmail | PartialUpdateSubEmail>(subEmail: T): RestOf<T> {
    return {
      ...subEmail,
      date: subEmail.date?.toJSON() ?? null,
      snoozedTo: subEmail.snoozedTo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSubEmail: RestSubEmail): ISubEmail {
    return {
      ...restSubEmail,
      date: restSubEmail.date ? dayjs(restSubEmail.date) : undefined,
      snoozedTo: restSubEmail.snoozedTo ? dayjs(restSubEmail.snoozedTo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSubEmail>): HttpResponse<ISubEmail> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSubEmail[]>): HttpResponse<ISubEmail[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
