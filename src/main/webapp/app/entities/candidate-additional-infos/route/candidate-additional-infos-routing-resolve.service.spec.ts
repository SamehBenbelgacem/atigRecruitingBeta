import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICandidateAdditionalInfos } from '../candidate-additional-infos.model';
import { CandidateAdditionalInfosService } from '../service/candidate-additional-infos.service';

import candidateAdditionalInfosResolve from './candidate-additional-infos-routing-resolve.service';

describe('CandidateAdditionalInfos routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CandidateAdditionalInfosService;
  let resultCandidateAdditionalInfos: ICandidateAdditionalInfos | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(CandidateAdditionalInfosService);
    resultCandidateAdditionalInfos = undefined;
  });

  describe('resolve', () => {
    it('should return ICandidateAdditionalInfos returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        candidateAdditionalInfosResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCandidateAdditionalInfos = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCandidateAdditionalInfos).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        candidateAdditionalInfosResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCandidateAdditionalInfos = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCandidateAdditionalInfos).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICandidateAdditionalInfos>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        candidateAdditionalInfosResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCandidateAdditionalInfos = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCandidateAdditionalInfos).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
